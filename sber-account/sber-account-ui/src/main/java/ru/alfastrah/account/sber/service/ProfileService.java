package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.backend.HasLogger;

import java.io.Serializable;

public interface ProfileService extends Serializable, HasLogger {
    void loadProfileData(String login);
}
