package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.model.UIText;

import java.io.Serializable;
import java.util.Map;

public interface UIService extends Serializable {
    Map<Integer, UIText> getTextMap(String login);
}
