package org.example;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.Assert.*;

public class MainWindowControllerTest {

    @Test
    public void onMousePressed() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");

        Object actual = scriptEngine.eval(String.valueOf(1 + 2));
        assertEquals(3, actual);

        actual = scriptEngine.eval(String.valueOf(100-45));
        assertEquals(55, actual);

        actual = scriptEngine.eval(String.valueOf(90/10+(9*10)+1));
        assertEquals(100, actual);

        actual = scriptEngine.eval(String.valueOf(100*(200-100)));
        assertEquals(10000, actual);
    }
}