package org.example;

import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.Assert.*;

public class MainWindowControllerTest {

    @Before
    public void setup() throws Exception {}

    @Test
    public void onMousePressed() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");

        Object actual = scriptEngine.eval(String.valueOf(1 + 2));
        String expected = "3";
        assertEquals(expected, actual);

        actual = scriptEngine.eval(String.valueOf(100-45));
        expected = "55";
        assertEquals(expected, actual);

        actual = scriptEngine.eval(String.valueOf(90/10+(9*10)+1));
        expected = "100";
        assertEquals(expected, actual);

        actual = scriptEngine.eval(String.valueOf(100*(200-100)));
        expected = "10000";
        assertEquals(expected, actual);
    }
}