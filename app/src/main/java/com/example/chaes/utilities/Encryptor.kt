package com.example.chaes.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Encryptor {
    private val password = "@ppl3_p!3_hue"
    private var hashMap = mapOf<String, String>()
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(message: String){
        // generating salt
        val saltRandom = SecureRandom()
        val salt = ByteArray(256)
        saltRandom.nextBytes(salt)
        // generating keySpec from salt and password
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1234, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        // generating IV
        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedMessage = cipher.doFinal(message.toByteArray())

//        Timber.d("Key = %s", String(keyBytes))
//        Timber.d("IV = %s", String(iv))
//        Timber.d("Salt = %s", String(salt))
//        Timber.d("Encrypted Message = %s", String(encryptedMessage))

        hashMap = mapOf(
            "iv" to Base64.getEncoder().encodeToString(iv),
            "salt" to Base64.getEncoder().encodeToString(salt),
            "encrypted" to Base64.getEncoder().encodeToString(encryptedMessage)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(){
        val salt = Base64.getDecoder().decode(hashMap["salt"]!!)
        val iv = Base64.getDecoder().decode(hashMap["iv"]!!)
        val encrypted = Base64.getDecoder().decode(hashMap["encrypted"]!!)

        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1234, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decrypted = cipher.doFinal(encrypted)

//        Timber.d("Key = %s", String(keyBytes))
//        Timber.d("IV = %s", String(iv!!))
//        Timber.d("Salt = %s", String(salt!!))
        Timber.d("Decrypted Message = %s", String(decrypted))
    }
}