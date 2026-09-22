#include <jni.h>
#include <string>

namespace runeveil {

std::string unveil(const unsigned char *blob, size_t n) {
    static const unsigned char key[] = {
        0x3e, 0x91, 0x07, 0xc4, 0x5a, 0x18, 0xe2, 0x6b, 0x2f, 0x80,
        0xd3, 0x44, 0x0b, 0x77, 0xa9, 0x1c, 0xf5, 0x32, 0x8e, 0x50,
        0x66, 0xb8, 0x0d
    };
    std::string out(n, 0);
    for (size_t i = 0; i < n; ++i) {
        unsigned char x = static_cast<unsigned char>(blob[i]);
        x = static_cast<unsigned char>((x - static_cast<unsigned char>((i * 11 + 3) & 0xFF)) & 0xFF);
        x = static_cast<unsigned char>(((x & 0x0F) << 4) | ((x & 0xF0) >> 4));
        x = static_cast<unsigned char>(x ^ key[i % 23]);
        out[i] = static_cast<char>(x);
    }
    return out;
}

void park(JNIEnv *env, jobject bag, jmethodID put,
        const unsigned char *slot, size_t slotN,
        const unsigned char *payload, size_t payloadN) {
    const std::string key = unveil(slot, slotN);
    const std::string value = unveil(payload, payloadN);
    env->CallObjectMethod(bag, put, env->NewStringUTF(key.c_str()), env->NewStringUTF(value.c_str()));
}

}

extern "C" JNIEXPORT jobject JNICALL
Java_com_p95ea315e_complete_1first_1called_1neon_cask_RuneCask_nativeRoster(JNIEnv *env, jobject) {
    jclass mapClass = env->FindClass("java/util/HashMap");
    if (mapClass == nullptr) return nullptr;
    jmethodID init = env->GetMethodID(mapClass, "<init>", "(I)V");
    jmethodID put = env->GetMethodID(mapClass, "put",
        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    jobject bag = env->NewObject(mapClass, init, 16);

    static const unsigned char k0k[] = {
        0x58, 0x28
    };
    static const unsigned char k0v[] = {
        0x68, 0x6c, 0x50, 0x6f, 0xc1, 0x5c, 0x21, 0x94, 0x6f, 0xc4,
        0x3c, 0x1e, 0x0e, 0x72, 0x19, 0x2f, 0xcc, 0xc2, 0x47, 0x37,
        0x00, 0x53, 0xdc, 0x65, 0xfa, 0x8d
    };
    runeveil::park(env, bag, put, k0k, sizeof(k0k), k0v, sizeof(k0v));

    static const unsigned char k1k[] = {
        0x58, 0x18
    };
    static const unsigned char k1v[] = {
        0x14, 0x7d, 0x9f, 0xe2, 0xf2, 0x21, 0x62, 0xf5, 0x3c, 0xf1
    };
    runeveil::park(env, bag, put, k1k, sizeof(k1k), k1v, sizeof(k1v));

    static const unsigned char k2k[] = {
        0x58, 0x48
    };
    static const unsigned char k2v[] = {
        0x14, 0x2c, 0x9b, 0x53, 0xf5, 0x1c, 0x62, 0x30, 0x1c, 0x60,
        0xfb, 0x4e, 0x7d, 0x32, 0x05, 0x7f
    };
    runeveil::park(env, bag, put, k2k, sizeof(k2k), k2v, sizeof(k2v));

    static const unsigned char k3k[] = {
        0x58, 0x38
    };
    static const unsigned char k3v[] = {
        0x14, 0x2c, 0x9b, 0x53, 0xf5, 0x1c, 0x62, 0x30, 0x1c, 0x60,
        0xfb, 0x4e, 0x7d, 0x32, 0x05, 0x8f
    };
    runeveil::park(env, bag, put, k3k, sizeof(k3k), k3v, sizeof(k3v));

    static const unsigned char k4k[] = {
        0x58, 0x68
    };
    static const unsigned char k4v[] = {
        0x69, 0xd9, 0x7f, 0x4e, 0x55, 0xcc, 0x41, 0x82, 0x4c
    };
    runeveil::park(env, bag, put, k4k, sizeof(k4k), k4v, sizeof(k4v));

    static const unsigned char k5k[] = {
        0x58, 0x58
    };
    static const unsigned char k5v[] = {
        0x69, 0xd9, 0x0c, 0x83, 0x65, 0xd1, 0x41, 0x82, 0x3c
    };
    runeveil::park(env, bag, put, k5k, sizeof(k5k), k5v, sizeof(k5v));

    static const unsigned char k6k[] = {
        0x58, 0x88
    };
    static const unsigned char k6v[] = {
        0xe9, 0x1b, 0x4e, 0x9c, 0x50
    };
    runeveil::park(env, bag, put, k6k, sizeof(k6k), k6v, sizeof(k6v));

    static const unsigned char k7k[] = {
        0x58, 0x78
    };
    static const unsigned char k7v[] = {
        0xd3, 0x3d, 0x4c, 0x53, 0xc2, 0xdc, 0xf2, 0x20, 0x1f, 0xb4,
        0x2f, 0x7e, 0x6d, 0x96, 0x29, 0x7f, 0x1c, 0xbe, 0x94, 0xe7,
        0x2f, 0x92, 0xe8, 0x90, 0x15, 0x39, 0x50, 0x52, 0x19, 0xaf,
        0x02, 0x39
    };
    runeveil::park(env, bag, put, k7k, sizeof(k7k), k7v, sizeof(k7v));
    return bag;
}
