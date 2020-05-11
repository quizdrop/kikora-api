/*
 *        kikora-api: API Wrapper for Kikora 2.0
 *        Copyright (C) 2020 Cubxity
 *
 *        This program is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU Lesser General Public License as published
 *        by the Free Software Foundation, either version 3 of the License, or
 *        (at your option) any later version.
 *
 *        This program is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU Lesser General Public License for more details.
 *
 *        You should have received a copy of the GNU Lesser General Public License
 *        along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.cubxity.kikora

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.cubxity.kikora.entity.KikoraContainerContent
import dev.cubxity.kikora.requests.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.BrowserUserAgent
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.Json
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

class KikoraAPI(private val sessionId: String) {
    companion object {
        private const val ENDPOINT = "https://feide-castor.kikora.no"
    }

    private val jackson = jacksonObjectMapper()
    private val client = HttpClient(OkHttp) {
        Json {
            serializer = JacksonSerializer()
        }
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage().apply {
                runBlocking { addCookie(Url("https://feide-castor.kikora.no"), Cookie("JSESSIONID", sessionId)) }
            }
        }
        BrowserUserAgent()
        expectSuccess = false
    }

    var languageCode: String = "nb"

    /**
     * @see KikoraPersonInfoRequest
     *
     * Fetch personal info
     */
    suspend fun personInfo() =
            get(KikoraPersonInfoRequest(languageCode))

    /**
     * @see KikoraTileSetRequest
     *
     * Fetch tile set, see:
     * https://feide-castor.kikora.no/beta/#/activities
     */
    suspend fun tileSet() =
            get(KikoraTileSetRequest(languageCode))

    /**
     * @param containerId container to fetch container set from
     * @param listPackages
     * @see KikoraContainerSetRequest
     *
     * Fetch container set
     * https://feide-castor.kikora.no/beta/#/activities/<TILE>/<CONTAINER>
     */
    suspend fun containerSet(containerId: String, listPackages: Boolean = true) =
            get(KikoraContainerSetRequest(containerId, listPackages, languageCode))

    /**
     * @param personId
     * @param containerIds containers to fetch data for
     * @see KikoraContainersRequest
     *
     * Fetch containers (data)
     * https://feide-castor.kikora.no/beta/#/activities/<TILE>/<CONTAINER>
     */
    suspend fun containers(personId: Long, containerIds: List<String>) =
            get(KikoraContainersRequest(personId, containerIds, languageCode))

    /**
     * @param containerId container to fetch the content for
     * @param userData whether to fetch user data or not
     * @see KikoraContainerContentRequest
     *
     * Fetch container content (exercises)
     * https://feide-castor.kikora.no/beta/#/calculation/t/<TILE>/<CONTAINER PATH>/<EXERCISE>
     */
    suspend fun containerContent(containerId: String, userData: Boolean = true) =
            get(KikoraContainerContentRequest(containerId, userData, languageCode))

    /**
     * @param containerId container that the exercise came from
     * @param exerciseId exercise to fetch the data for
     * @see KikoraExercisePersonRequest
     *
     * Fetch personal exercise data (progress, stats, etc)
     * https://feide-castor.kikora.no/beta/#/calculation/t/<TILE>/<CONTAINER PATH>/<EXERCISE>
     */
    suspend fun exercisePerson(containerId: String, exerciseId: String) =
            get(KikoraExercisePersonRequest(exerciseId, containerId, languageCode))

    @Throws(KikoraException::class)
    private suspend inline fun <reified R : KikoraResponse> get(request: KikoraRequest<R>): R {
        val res = client.get<HttpResponse>("$ENDPOINT/json2") {
            val obj = ObjectNode(jackson.nodeFactory)
            obj.set(request.id, jackson.valueToTree<ObjectNode>(request))
            parameter("request", obj)
        }
        val list: ArrayNode = jackson.readValue(res.readText())

        if (res.status.isSuccess())
            return jackson.treeToValue(list.first())
        else
            throw jackson.treeToValue<KikoraException>(list.first()["exception"])
    }
}