package de.dhbw.dontgetangry.ui;

import de.dhbw.dontgetangry.model.Player;

public interface UIEventListener {

    void onRollDiceByUI();

    void onSetPositionByUI(int character, boolean forward);

    void onEndTurnByUI();

}
