package com.zhuinden.espressohelpersample

import junit.framework.Assert
import org.junit.Assert.assertNotEquals

inline fun verifyAssertion(assertion: () -> Unit){
    val errorMessage = "Test should've failed"
    try{
        assertion()
        Assert.fail(errorMessage)
    }catch (e: AssertionError){
        assertNotEquals(e.message, errorMessage)
    }
}