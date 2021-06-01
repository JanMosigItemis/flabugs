package de.itemis.mosig.flabugs.lstrlenw;

import static java.nio.charset.StandardCharsets.UTF_16LE;
import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_POINTER;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAddress;

public class LStrLenW {

    private static MethodHandle methodHandle;

    static {
        var kernel32 = LibraryLookup.ofLibrary("Kernel32");
        var lstrlenw = kernel32.lookup("lstrlenW");
        var functionDescriptor = FunctionDescriptor.of(C_INT, C_POINTER);
        var methodType = MethodType.methodType(int.class, MemoryAddress.class);
        methodHandle = CLinker.getInstance().downcallHandle(lstrlenw.get(), methodType, functionDescriptor);
    }

    public static int myMethod(String text) {
        try {
            try (var txt = CLinker.toCString(text, UTF_16LE)) {
                return (int) methodHandle.invokeExact(txt.address());
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
