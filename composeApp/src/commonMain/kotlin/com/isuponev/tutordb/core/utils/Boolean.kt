package com.isuponev.tutordb.core.utils

/**
 * Checks if all given boolean conditions are true using lazy evaluation.
 *
 * This function provides a convenient way to verify that every condition in a variable number
 * of boolean lambdas evaluates to true. It uses lazy evaluation - conditions are only computed
 * when needed, and evaluation stops immediately upon encountering the first `false` result.
 *
 * The evaluation is **short-circuiting** - the function returns `false` immediately upon encountering
 * the first condition that evaluates to `false`, without executing the remaining conditions.
 * This is particularly useful when some conditions are computationally expensive or have side effects.
 *
 * Note: For empty conditions, this function follows the principle of
 * [vacuous truth](https://en.wikipedia.org/wiki/Vacuous_truth) and returns `true`.
 *
 * Important: Since conditions are lambdas, they may throw exceptions. Ensure proper
 * error handling within the condition lambdas if needed.
 *
 * @param conditions Variable number of lambda expressions that return boolean conditions to evaluate.
 * @return `true` if all conditions evaluate to true, `false` otherwise.
 *         Returns `true` if no conditions are provided (vacuous truth).
 *
 * @throws Exception if any of the condition lambdas throws an exception.
 *
 * @see [BooleanArray.all]
 * @see [kotlin.collections.all]
 *
 */
fun all(vararg conditions: () -> Boolean): Boolean = conditions.all { it() }
