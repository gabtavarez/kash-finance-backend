package tavarez.kash_finance.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class Relogios {

    private Relogios() {
    }

    public static LocalDateTime agora() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
