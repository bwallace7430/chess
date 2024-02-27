package server;

import java.util.Collection;

public record ListGameResponse(Collection<GameListEntry> games) {
}
