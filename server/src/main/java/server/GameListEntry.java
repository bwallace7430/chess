package server;

import java.util.Collection;

public record GameListEntry(int gameID, String whiteUsername, String blackUsername, String gameName) {}

