package com.dwhr.myexplorer.data.security

import android.util.Base64
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object BackupCrypto {
    private const val SALT_SIZE = 16
    private const val IV_SIZE = 12
    private const val KEY_SIZE_BITS = 256
    private const val ITERATIONS = 120_000
    private const val GCM_TAG_BITS = 128

    fun encrypt(payload: ByteArray, password: CharArray): String {
        val salt = ByteArray(SALT_SIZE).also(SecureRandom()::nextBytes)
        val iv = ByteArray(IV_SIZE).also(SecureRandom()::nextBytes)
        val secret = deriveKey(password, salt)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secret, GCMParameterSpec(GCM_TAG_BITS, iv))
        val encrypted = cipher.doFinal(payload)
        val buffer = ByteBuffer.allocate(SALT_SIZE + IV_SIZE + encrypted.size)
            .put(salt)
            .put(iv)
            .put(encrypted)
        return Base64.encodeToString(buffer.array(), Base64.NO_WRAP)
    }

    fun decrypt(encodedPayload: String, password: CharArray): ByteArray {
        val decoded = Base64.decode(encodedPayload, Base64.NO_WRAP)
        val buffer = ByteBuffer.wrap(decoded)
        val salt = ByteArray(SALT_SIZE).also(buffer::get)
        val iv = ByteArray(IV_SIZE).also(buffer::get)
        val encrypted = ByteArray(buffer.remaining()).also(buffer::get)
        val secret = deriveKey(password, salt)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secret, GCMParameterSpec(GCM_TAG_BITS, iv))
        return cipher.doFinal(encrypted)
    }

    private fun deriveKey(password: CharArray, salt: ByteArray): SecretKeySpec {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_SIZE_BITS)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
    }
}
