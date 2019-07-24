package com.falconxrobotics.website.application.error;

public interface CustomError {
    <T> T getStatus();

    <T> T getMessage();

    <T extends Exception> T getException();
}