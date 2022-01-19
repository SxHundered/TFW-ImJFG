package com.tfw.scoreboard;

import com.tfw.configuration.Style;

public class IScoreboardException extends Exception{

    public IScoreboardException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return Style.translate(super.getMessage());
    }
}
