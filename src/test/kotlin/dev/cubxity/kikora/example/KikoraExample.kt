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

package dev.cubxity.kikora.example

import dev.cubxity.kikora.KikoraAPI
import dev.cubxity.kikora.utils.filterLeaf

/**
 * kikora-api utilizes ktor-client and Kotlin coroutines
 * Learn more about coroutines here: https://kotlinlang.org/docs/reference/coroutines-overview.html
 */
suspend fun main() {
    // TODO: Add JSESSIONID to environment variables
    // You can retrieve this by going to https://feide-castor.kikora.no/beta/#
    // and find the cookie in inspect (Ctrl+Shift+I)
    val api = KikoraAPI(System.getenv("JSESSIONID"))

    // Select the first tile
    val tile = api.tileSet().tiles.first()
    println(tile)

    // Get tile's containers
    val containers = api.containerSet(tile.containers.first().containerId).containers.map { it.containerContent }

    // Find the first leaf container
    val container = containers.filterLeaf().first()
    println(container)

    // Fetch the content for the selected container
    val leaf = api.containerContent(container.containerId).leafContainer ?: error("Leaf container not found")

    // Select the first exercise
    val exercise = leaf.containerExercises.first().exercise
    val exerciseId = exercise.exerciseDefinition.exerciseId
    println(exercise)

    // Fetch user's exercise data
    val exercisePerson = api.exercisePerson(container.containerId, "$exerciseId")
    println(exercisePerson)
}