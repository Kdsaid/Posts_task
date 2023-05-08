package com.example.testapp.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.testapp.R

private const val TAG = "clearBackStackAndNavigate"

/**
 * clear navigation graph back stack and then navigate to destination
 * */
fun Fragment.clearBackStackAndNavigate(
    @IdRes graphID: Int,
    @IdRes navId: Int,
    args: Bundle? = null,
    inclusive: Boolean = false,
) {
    findNavControllerSafely()?.navigate(
        navId, args, NavOptions.Builder()
            .setPopUpTo(graphID, inclusive)
            .build()
    )
    logCurrentScreen("navigate to")
}

@SuppressLint("LongLogTag")
fun Fragment.logCurrentScreen(navigationMessage: String) {
    findNavControllerSafely()?.currentBackStackEntry?.destination?.label?.let {
        Log.i(TAG, "$navigationMessage -> $it")
    }
}


@SuppressLint("LongLogTag")
fun Fragment.navigateSafeWithAction(@IdRes actionId: Int, arguments: Bundle? = null) {
    val safeNavController = findNavControllerSafely() ?: return
    val (navController, currentDestination, graph) = with(safeNavController) {
        Triple(this, currentDestination, graph)
    }
    val action = currentDestination?.getAction(actionId) ?: graph.getAction(actionId)
    val navLogger = {

        Log.i(
            TAG, "Cannot navigate  ${this::class.simpleName}  using  action: ${
                resources.getResourceEntryName(
                    actionId
                )
            }"
        )

    }
    if (action != null && currentDestination?.id != action.destinationId) {
        runCatching {
            navController.navigate(actionId, arguments, getDefaultNavOptions())
            logCurrentScreen("navigate to")
        }.onFailure {
            Log.i(TAG, "Navigation error: $it")
            navLogger.invoke()
        }
    } else {
        navLogger.invoke()
    }
}

fun Fragment.findNavControllerSafely(): NavController? {
    return if (isAdded) findNavController() else null
}


/**
 * use default navController method to pop in the app and inclusive pop is false by default
 */
fun Fragment.popWithIdTo(@IdRes navId: Int, inclusive: Boolean = false) {
    findNavControllerSafely()?.popBackStack(navId, inclusive)
    logCurrentScreen("pop to")

}

/**
 * use default navController method to pop to the previous fragment in the stack
 */
fun Fragment.navigateUp() {
    findNavControllerSafely()?.navigateUp()
    logCurrentScreen("pop to")
}

/**
 * use default navController method to navigate in the app and  attaching default nav options
 */
fun Fragment.navigateTo(action: NavDirections) {
    findNavController().navigate(action, getDefaultNavOptions())

}

fun Fragment.navigateTo(id: Int) {
    findNavController().apply {
        navigate(id)
        getDefaultNavOptions()
    }
}

fun Fragment.navigateToPopBackStackFromTo(fromId: Int, toId: Int) {
    findNavController().apply {
        getDefaultNavOptions()
        popBackStack(fromId, true)
        navigate(toId)
    }
}

fun Fragment.navigateToPopBackStack(id: Int) {
    findNavController().apply {
        navigate(id)
        backQueue.clear()
    }
}

/**
 * Method provides default navOptions that will be applied on all of the navigation
 */
fun getDefaultNavOptions() = navOptions {
    anim {
        enter = R.anim.fragment_fade_enter
        exit = R.anim.fragment_fade_exit
        popEnter = R.anim.fragment_fade_enter
        popExit = R.anim.fragment_fade_exit
    }
}


