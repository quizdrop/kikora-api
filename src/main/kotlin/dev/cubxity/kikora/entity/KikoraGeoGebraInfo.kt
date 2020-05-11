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

package dev.cubxity.kikora.entity

data class KikoraGeoGebraInfo(
        val geoId: Int,
        val geoCommand: String,
        val base64: String,
        val appName: String,
        val geoSave: Boolean,
        val webSimple: Boolean,
        val showAlgebraInput: Boolean,
        val showMenubar: Boolean,
        val showToolbar: Boolean,
        val showToolbarHelp: Boolean,
        val enableRightClick: Boolean,
        val enableLabelDrags: Boolean,
        val enableShiftDragZoom: Boolean,
        val allowStylebar: Boolean
)