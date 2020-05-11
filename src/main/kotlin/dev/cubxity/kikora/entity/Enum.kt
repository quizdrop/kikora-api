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

enum class KikoraContainerContentType { PACKAGE, LEAF }

enum class KikoraExerciseType {
    ECHO_PLAIN,
    ECHO_MCHOICE,
    ECHO_MINPUT,
    GEO_ECHO_PLAIN,
    GEO_ECHO_MCHOICE,
    ECHO_MPLEX,
    GEO_AUTO,
    GEO_MANUAL,
    VICTORY,
    RAMBUKK,
    TEACHER_PLAIN,
    TEACHER_GEO,
    ADAPTIVE_CUBE,
    PROGRAMMING_TEXT,
    PROGRAMMING_BLOCKLY
}

enum class KikoraExerciseStatus { WRONG, INCOMPLETE, NOT_STARTED, FINISHED, UNKNOWN, COMPLETE }

enum class KikoraExpressionStatus {
    ANSWER_TROPHY,
    CORRECT_GREEN,
    WRONG_RED,
    KEY,
    NO_EVAL_TROPHY,
    UNKNOWN,
    DEFAULT
}

enum class KikoraPresentationType { NORMAL, STAR, EXPLANATION }