import java.io.*;

public class AES {
    public final byte[][] Rcon = {
            {
                    (byte) 0x8d, (byte) 0x01, (byte) 0x02, (byte) 0x04,
                    (byte) 0x08, (byte) 0x10, (byte) 0x20, (byte) 0x40,
                    (byte) 0x80, (byte) 0x1b, (byte) 0x36, (byte) 0x6c,
                    (byte) 0xd8, (byte) 0xab, (byte) 0x4d
            },
            {
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            {
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            {
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            }
    };

    public final byte[][] C = {
            {
                    2, 3, 1, 1
            },
            {
                    1, 2, 3, 1
            },
            {
                    1, 1, 2, 3
            },
            {
                    3, 1, 1, 2
            }
    };

    public final byte[] Sbox = {
                    (byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b,
                    (byte) 0xf2, (byte) 0x6b, (byte) 0x6f, (byte) 0xc5,
                    (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b,
                    (byte) 0xfe, (byte) 0xd7, (byte) 0xab, (byte) 0x76,

                    (byte) 0xca, (byte) 0x82, (byte) 0xc9, (byte) 0x7d,
                    (byte) 0xfa, (byte) 0x59, (byte) 0x47, (byte) 0xf0,
                    (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf,
                    (byte) 0x9c, (byte) 0xa4, (byte) 0x72, (byte) 0xc0,

                    (byte) 0xb7, (byte) 0xfd, (byte) 0x93, (byte) 0x26,
                    (byte) 0x36, (byte) 0x3f, (byte) 0xf7, (byte) 0xcc,
                    (byte) 0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1,
                    (byte) 0x71, (byte) 0xd8, (byte) 0x31, (byte) 0x15,

                    (byte) 0x04, (byte) 0xc7, (byte) 0x23, (byte) 0xc3,
                    (byte) 0x18, (byte) 0x96, (byte) 0x05, (byte) 0x9a,
                    (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xe2,
                    (byte) 0xeb, (byte) 0x27, (byte) 0xb2, (byte) 0x75,

                    (byte) 0x09, (byte) 0x83, (byte) 0x2c, (byte) 0x1a,
                    (byte) 0x1b, (byte) 0x6e, (byte) 0x5a, (byte) 0xa0,
                    (byte) 0x52, (byte) 0x3b, (byte) 0xd6, (byte) 0xb3,
                    (byte) 0x29, (byte) 0xe3, (byte) 0x2f, (byte) 0x84,

                    (byte) 0x53, (byte) 0xd1, (byte) 0x00, (byte) 0xed,
                    (byte) 0x20, (byte) 0xfc, (byte) 0xb1, (byte) 0x5b,
                    (byte) 0x6a, (byte) 0xcb, (byte) 0xbe, (byte) 0x39,
                    (byte) 0x4a, (byte) 0x4c, (byte) 0x58, (byte) 0xcf,

                    (byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb,
                    (byte) 0x43, (byte) 0x4d, (byte) 0x33, (byte) 0x85,
                    (byte) 0x45, (byte) 0xf9, (byte) 0x02, (byte) 0x7f,
                    (byte) 0x50, (byte) 0x3c, (byte) 0x9f, (byte) 0xa8,

                    (byte) 0x51, (byte) 0xa3, (byte) 0x40, (byte) 0x8f,
                    (byte) 0x92, (byte) 0x9d, (byte) 0x38, (byte) 0xf5,
                    (byte) 0xbc, (byte) 0xb6, (byte) 0xda, (byte) 0x21,
                    (byte) 0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2,

                    (byte) 0xcd, (byte) 0x0c, (byte) 0x13, (byte) 0xec,
                    (byte) 0x5f, (byte) 0x97, (byte) 0x44, (byte) 0x17,
                    (byte) 0xc4, (byte) 0xa7, (byte) 0x7e, (byte) 0x3d,
                    (byte) 0x64, (byte) 0x5d, (byte) 0x19, (byte) 0x73,

                    (byte) 0x60, (byte) 0x81, (byte) 0x4f, (byte) 0xdc,
                    (byte) 0x22, (byte) 0x2a, (byte) 0x90, (byte) 0x88,
                    (byte) 0x46, (byte) 0xee, (byte) 0xb8, (byte) 0x14,
                    (byte) 0xde, (byte) 0x5e, (byte) 0x0b, (byte) 0xdb,

                    (byte) 0xe0, (byte) 0x32, (byte) 0x3a, (byte) 0x0a,
                    (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5c,
                    (byte) 0xc2, (byte) 0xd3, (byte) 0xac, (byte) 0x62,
                    (byte) 0x91, (byte) 0x95, (byte) 0xe4, (byte) 0x79,

                    (byte) 0xe7, (byte) 0xc8, (byte) 0x37, (byte) 0x6d,
                    (byte) 0x8d, (byte) 0xd5, (byte) 0x4e, (byte) 0xa9,
                    (byte) 0x6c, (byte) 0x56, (byte) 0xf4, (byte) 0xea,
                    (byte) 0x65, (byte) 0x7a, (byte) 0xae, (byte) 0x08,

                    (byte) 0xba, (byte) 0x78, (byte) 0x25, (byte) 0x2e,
                    (byte) 0x1c, (byte) 0xa6, (byte) 0xb4, (byte) 0xc6,
                    (byte) 0xe8, (byte) 0xdd, (byte) 0x74, (byte) 0x1f,
                    (byte) 0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a,

                    (byte) 0x70, (byte) 0x3e, (byte) 0xb5, (byte) 0x66,
                    (byte) 0x48, (byte) 0x03, (byte) 0xf6, (byte) 0x0e,
                    (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xb9,
                    (byte) 0x86, (byte) 0xc1, (byte) 0x1d, (byte) 0x9e,

                    (byte) 0xe1, (byte) 0xf8, (byte) 0x98, (byte) 0x11,
                    (byte) 0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94,
                    (byte) 0x9b, (byte) 0x1e, (byte) 0x87, (byte) 0xe9,
                    (byte) 0xce, (byte) 0x55, (byte) 0x28, (byte) 0xdf,

                    (byte) 0x8c, (byte) 0xa1, (byte) 0x89, (byte) 0x0d,
                    (byte) 0xbf, (byte) 0xe6, (byte) 0x42, (byte) 0x68,
                    (byte) 0x41, (byte) 0x99, (byte) 0x2d, (byte) 0x0f,
                    (byte) 0xb0, (byte) 0x54, (byte) 0xbb, (byte) 0x16
    };

    public final byte[] invSbox = {
                    (byte) 0x52, (byte) 0x09, (byte) 0x6a, (byte) 0xd5,
                    (byte) 0x30, (byte) 0x36, (byte) 0xa5, (byte) 0x38,
                    (byte) 0xbf, (byte) 0x40, (byte) 0xa3, (byte) 0x9e,
                    (byte) 0x81, (byte) 0xf3, (byte) 0xd7, (byte) 0xfb,

                    (byte) 0x7c, (byte) 0xe3, (byte) 0x39, (byte) 0x82,
                    (byte) 0x9b, (byte) 0x2f, (byte) 0xff, (byte) 0x87,
                    (byte) 0x34, (byte) 0x8e, (byte) 0x43, (byte) 0x44,
                    (byte) 0xc4, (byte) 0xde, (byte) 0xe9, (byte) 0xcb,

                    (byte) 0x54, (byte) 0x7b, (byte) 0x94, (byte) 0x32,
                    (byte) 0xa6, (byte) 0xc2, (byte) 0x23, (byte) 0x3d,
                    (byte) 0xee, (byte) 0x4c, (byte) 0x95, (byte) 0x0b,
                    (byte) 0x42, (byte) 0xfa, (byte) 0xc3, (byte) 0x4e,

                    (byte) 0x08, (byte) 0x2e, (byte) 0xa1, (byte) 0x66,
                    (byte) 0x28, (byte) 0xd9, (byte) 0x24, (byte) 0xb2,
                    (byte) 0x76, (byte) 0x5b, (byte) 0xa2, (byte) 0x49,
                    (byte) 0x6d, (byte) 0x8b, (byte) 0xd1, (byte) 0x25,

                    (byte) 0x72, (byte) 0xf8, (byte) 0xf6, (byte) 0x64,
                    (byte) 0x86, (byte) 0x68, (byte) 0x98, (byte) 0x16,
                    (byte) 0xd4, (byte) 0xa4, (byte) 0x5c, (byte) 0xcc,
                    (byte) 0x5d, (byte) 0x65, (byte) 0xb6, (byte) 0x92,

                    (byte) 0x6c, (byte) 0x70, (byte) 0x48, (byte) 0x50,
                    (byte) 0xfd, (byte) 0xed, (byte) 0xb9, (byte) 0xda,
                    (byte) 0x5e, (byte) 0x15, (byte) 0x46, (byte) 0x57,
                    (byte) 0xa7, (byte) 0x8d, (byte) 0x9d, (byte) 0x84,

                    (byte) 0x90, (byte) 0xd8, (byte) 0xab, (byte) 0x00,
                    (byte) 0x8c, (byte) 0xbc, (byte) 0xd3, (byte) 0x0a,
                    (byte) 0xf7, (byte) 0xe4, (byte) 0x58, (byte) 0x05,
                    (byte) 0xb8, (byte) 0xb3, (byte) 0x45, (byte) 0x06,

                    (byte) 0xd0, (byte) 0x2c, (byte) 0x1e, (byte) 0x8f,
                    (byte) 0xca, (byte) 0x3f, (byte) 0x0f, (byte) 0x02,
                    (byte) 0xc1, (byte) 0xaf, (byte) 0xbd, (byte) 0x03,
                    (byte) 0x01, (byte) 0x13, (byte) 0x8a, (byte) 0x6b,

                    (byte) 0x3a, (byte) 0x91, (byte) 0x11, (byte) 0x41,
                    (byte) 0x4f, (byte) 0x67, (byte) 0xdc, (byte) 0xea,
                    (byte) 0x97, (byte) 0xf2, (byte) 0xcf, (byte) 0xce,
                    (byte) 0xf0, (byte) 0xb4, (byte) 0xe6, (byte) 0x73,

                    (byte) 0x96, (byte) 0xac, (byte) 0x74, (byte) 0x22,
                    (byte) 0xe7, (byte) 0xad, (byte) 0x35, (byte) 0x85,
                    (byte) 0xe2, (byte) 0xf9, (byte) 0x37, (byte) 0xe8,
                    (byte) 0x1c, (byte) 0x75, (byte) 0xdf, (byte) 0x6e,

                    (byte) 0x47, (byte) 0xf1, (byte) 0x1a, (byte) 0x71,
                    (byte) 0x1d, (byte) 0x29, (byte) 0xc5, (byte) 0x89,
                    (byte) 0x6f, (byte) 0xb7, (byte) 0x62, (byte) 0x0e,
                    (byte) 0xaa, (byte) 0x18, (byte) 0xbe, (byte) 0x1b,

                    (byte) 0xfc, (byte) 0x56, (byte) 0x3e, (byte) 0x4b,
                    (byte) 0xc6, (byte) 0xd2, (byte) 0x79, (byte) 0x20,
                    (byte) 0x9a, (byte) 0xdb, (byte) 0xc0, (byte) 0xfe,
                    (byte) 0x78, (byte) 0xcd, (byte) 0x5a, (byte) 0xf4,

                    (byte) 0x1f, (byte) 0xdd, (byte) 0xa8, (byte) 0x33,
                    (byte) 0x88, (byte) 0x07, (byte) 0xc7, (byte) 0x31,
                    (byte) 0xb1, (byte) 0x12, (byte) 0x10, (byte) 0x59,
                    (byte) 0x27, (byte) 0x80, (byte) 0xec, (byte) 0x5f,

                    (byte) 0x60, (byte) 0x51, (byte) 0x7f, (byte) 0xa9,
                    (byte) 0x19, (byte) 0xb5, (byte) 0x4a, (byte) 0x0d,
                    (byte) 0x2d, (byte) 0xe5, (byte) 0x7a, (byte) 0x9f,
                    (byte) 0x93, (byte) 0xc9, (byte) 0x9c, (byte) 0xef,

                    (byte) 0xa0, (byte) 0xe0, (byte) 0x3b, (byte) 0x4d,
                    (byte) 0xae, (byte) 0x2a, (byte) 0xf5, (byte) 0xb0,
                    (byte) 0xc8, (byte) 0xeb, (byte) 0xbb, (byte) 0x3c,
                    (byte) 0x83, (byte) 0x53, (byte) 0x99, (byte) 0x61,

                    (byte) 0x17, (byte) 0x2b, (byte) 0x04, (byte) 0x7e,
                    (byte) 0xba, (byte) 0x77, (byte) 0xd6, (byte) 0x26,
                    (byte) 0xe1, (byte) 0x69, (byte) 0x14, (byte) 0x63,
                    (byte) 0x55, (byte) 0x21, (byte) 0x0c, (byte) 0x7d
    };

    public final byte[][] invC = {
            {
                    0x0e, 0x0b, 0x0d, 0x09
            },
            {
                    0x09, 0x0e, 0x0b, 0x0d
            },
            {
                    0x0d, 0x09, 0x0e, 0x0b
            },
            {
                    0x0b, 0x0d, 0x09, 0x0e
            }
    };

    private final int BYTES_BLOCK = 16;
    private final int BYTES_WORD = 4;
    private final int ROUNDS = 10;
    private final int WORDS_BLOCK = 4;

    void xorRoundKey(byte[] block, byte[] key) {
        for (int i = 0; i < BYTES_BLOCK; i++) {
            block[i] = (byte) (block[i] ^ key[i]);
        }
    }

    void subBytes(byte[] block) {
        for (int i = 0; i < BYTES_BLOCK; i++) {
            block[i] = Sbox[Byte.toUnsignedInt(block[i])];
        }
    }

    void invSubBytes(byte[] block) {
        for (int i = 0; i < BYTES_BLOCK; i++) {
            block[i] = invSbox[Byte.toUnsignedInt(block[i])];
        }
    }

    void shiftRows(byte[] block) {
        byte[] tmp = new byte[BYTES_BLOCK];
        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                tmp[i * BYTES_WORD + j] = block[j + (i + j) % WORDS_BLOCK * WORDS_BLOCK];
            }
        }

        System.arraycopy(tmp, 0, block, 0, BYTES_BLOCK);
    }

    void invShiftRows(byte[] block) {
        byte[] tmp = new byte[BYTES_BLOCK];
        for (int i = 0; i < WORDS_BLOCK; i++) {
            for (int j = 0; j < BYTES_WORD; j++) {
                tmp[i * BYTES_WORD + j] = block[j + (i + WORDS_BLOCK - j) % WORDS_BLOCK * WORDS_BLOCK];
            }
        }

        System.arraycopy(tmp, 0, block, 0, BYTES_BLOCK);
    }

    byte nxtime(byte a, int n) {
        for (int i = 0; i < n; i++) {
            int highbit = Byte.toUnsignedInt(a) & 0x80;
            int shl = (a << 1) & 0xff;
            if (highbit != 0) {
                a = (byte) shl;
            } else {
                a = (byte) (shl ^ 0x1b);
            }
        }

        return a;
    }

    byte multiplyGalois(byte a, byte b) {
        byte res = 0;
        for (int i = 0; i < 8; i++) {
            int mask = 1 << i;
            if ((mask & Byte.toUnsignedInt(b)) != 0) {
                res = (byte) (res ^ nxtime(a, i));
            }
        }

        return res;
    }

    void mixColumns(byte[] block) {
        byte[] transpone = new byte[BYTES_BLOCK];
        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                transpone[i * BYTES_WORD + j] = block[j * BYTES_WORD + i];
            }
        }

        byte[] res = new byte[BYTES_BLOCK];
        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                for (int k = 0; k < WORDS_BLOCK; k++) {
                    res[i * BYTES_WORD + j] = (byte) (res[i * BYTES_WORD + j] ^
                                                multiplyGalois(transpone[k * BYTES_WORD + j], C[i][k]));
                }
            }
        }

        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                block[i * BYTES_WORD + j] = res[j * BYTES_WORD + i];
            }
        }
    }

    void invMixColumns(byte[] block) {
        byte[] transpone = new byte[BYTES_BLOCK];
        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                transpone[i * BYTES_WORD + j] = block[j * BYTES_WORD + i];
            }
        }

        byte[] res = new byte[BYTES_BLOCK];
        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                for (int k = 0; k < WORDS_BLOCK; k++) {
                    res[i * BYTES_WORD + j] = (byte) (res[i * BYTES_WORD + j] ^
                            multiplyGalois(transpone[k * BYTES_WORD + j], invC[i][k]));
                }
            }
        }

        for (int i = 0; i < BYTES_WORD; i++) {
            for (int j = 0; j < WORDS_BLOCK; j++) {
                block[i * BYTES_WORD + j] = res[j * BYTES_WORD + i];
            }
        }
    }

    void nextRoundKey(byte[] key, int n) {
        byte[] newKey = new byte[BYTES_BLOCK];
        byte[] firstWord = new byte[4];
        for (int i = 0; i < BYTES_WORD; i++) {
            firstWord[i] = key[(i + 1) % BYTES_WORD + 12];
            firstWord[i] = Sbox[Byte.toUnsignedInt(firstWord[i])];
        }

        firstWord[0] = (byte) (firstWord[0] ^ Rcon[0][n + 1]);

        for (int i = 0; i < BYTES_WORD; i++) {
            newKey[i] = (byte) (firstWord[i] ^ key[i]);
        }

        for (int i = 4; i < BYTES_BLOCK; i++) {
            newKey[i] = (byte) (newKey[i - 4] ^ key[i]);
        }

        System.arraycopy(newKey, 0, key, 0, BYTES_BLOCK);
    }

    void enciph(byte[] block, byte[] res, byte[] key) {
        System.out.println("ENCIPH BEGIN");
        System.arraycopy(block, 0, res, 0, BYTES_BLOCK);
        printBlock(res);

        xorRoundKey(res, key);

        for (int i = 0; i < ROUNDS; i++) {
            subBytes(res);
            shiftRows(res);

            if (i < ROUNDS - 1) {
                mixColumns(res);
            }

            nextRoundKey(key, i);
            xorRoundKey(res, key);
        }

        System.out.println("ENCIPH END");
    }

    void deciph(byte[] block, byte[] res, byte[] key) {
        System.arraycopy(block, 0, res, 0, BYTES_BLOCK);
        byte[][] roundKeys = new byte[ROUNDS + 1][BYTES_BLOCK];
        System.arraycopy(key, 0, roundKeys[0], 0, BYTES_BLOCK);

        for (int i = 1; i <= ROUNDS; i++) {
            nextRoundKey(key, i - 1);
            System.arraycopy(key, 0, roundKeys[i], 0, BYTES_BLOCK);
        }

        xorRoundKey(res, roundKeys[ROUNDS]);

        for (int i = ROUNDS - 1; i >= 0; i--) {
            invShiftRows(res);
            invSubBytes(res);

            xorRoundKey(res, roundKeys[i]);
            if (i > 0) {
                invMixColumns(res);
            }
        }
    }

    public void enciphFile(String src, String keyfile, String dst) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dst, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(src);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        FileInputStream fileKeyStream = new FileInputStream(keyfile);
        DataInputStream dataKeyStream = new DataInputStream(fileKeyStream);

        byte[] key = new byte[16];
        int i = 0;
        while (dataKeyStream.available() > 0) {
            key[i] = dataKeyStream.readByte();
            i++;
        }

        printBlock(key);

        while (dataInputStream.available() > 0) {
            byte[] block = new byte[BYTES_BLOCK];
            i = 0;
            while (dataInputStream.available() > 0 && i < BYTES_BLOCK) {
                block[i] = dataInputStream.readByte();
                i++;
            }
            System.out.println("\n");
            byte[] res = new byte[BYTES_BLOCK];
            enciph(block, res, key);
            for (int j = 0; j < BYTES_BLOCK; j++) {
                dataOutputStream.writeByte(res[j]);
            }
            printBlock(res);
        }

        fileInputStream.close();
        fileOutputStream.close();
        fileKeyStream.close();
    }

    public void deciphFile(String src, String keyfile, String dst) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dst, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(src);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        FileInputStream fileKeyStream = new FileInputStream(keyfile);
        DataInputStream dataKeyStream = new DataInputStream(fileKeyStream);

        byte[] key = new byte[16];
        int i = 0;
        while (dataKeyStream.available() > 0) {
            key[i] = dataKeyStream.readByte();
            i++;
        }

        printBlock(key);

        while (dataInputStream.available() > 0) {
            byte[] block = new byte[BYTES_BLOCK];
            i = 0;
            while (dataInputStream.available() > 0 && i < BYTES_BLOCK) {
                block[i] = dataInputStream.readByte();
                i++;
            }
            System.out.println("\n");
            byte[] res = new byte[BYTES_BLOCK];
            deciph(block, res, key);
            for (int j = 0; j < BYTES_BLOCK; j++) {
                dataOutputStream.writeByte(res[j]);
            }
            printBlock(res);
        }

        fileInputStream.close();
        fileOutputStream.close();
        fileKeyStream.close();
    }

    void printBlock(byte[] block) {
        for (int i = 0; i < block.length; i++) {
            System.out.printf("%x", block[i]);
        }
        System.out.println("\n");
    }

    public AES() {
    }
}
