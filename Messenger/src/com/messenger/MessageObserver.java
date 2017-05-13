package com.messenger;


public interface MessageObserver {
    void notify(MessageWrapper messageWrapper);
}
