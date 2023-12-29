package com.ccdr.labyrinth.game;

import java.io.InputStream;
import java.util.Set;

public interface Loadable<T> {
    public T LoadOne(InputStream file);
    public Set<T> LoadMany(InputStream file);
}
