package daw.ka.informejtycy.anticheat.server;

import daw.ka.informejtycy.anticheat.Evidence;

import java.util.List;

public record Verdict(Status status, List<String> reasons, Evidence evidence) {
    public enum Status {
        OK,
        SUSPICIOUS,
        TIMEOUT,
        TAMPERED,
        FORBIDDEN
    }
}
