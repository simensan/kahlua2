package se.krka.kahlua.cldc11;

import se.krka.kahlua.stdlib.BaseLib;
import se.krka.kahlua.stdlib.CoroutineLib;
import se.krka.kahlua.stdlib.StringLib;
import se.krka.kahlua.stdlib.TableLib;
import se.krka.kahlua.vm.*;

public class CLDC11Platform implements Platform {
    public double pow(double x, double y) {
        return MathLib.pow(x, y);
    }

    public KahluaTable newTable() {
        return new KahluaTableImpl();
    }

    public KahluaTable newEnvironment() {
        KahluaTable env = newTable();

        env.rawset("_G", env);
        env.rawset("_VERSION", "Kahlua 2 for CLDC 1.1");

        BaseLib.register(env);
        MathLib.register(env);
        StringLib.register(this, env);
        CoroutineLib.register(this, env);
        TableLib.register(this, env);

        KahluaThread workerThread = KahluaUtil.getWorkerThread(this, env);
        KahluaUtil.setupLibrary(env, workerThread, "/stdlib");

        return env;
    }

}
