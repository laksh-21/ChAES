package com.example.chaes.utilities

import android.os.Build
import com.example.chaes.models.Message
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
    private val ivKey = "iv"
    private val saltKey = "salt"
    private val messageKey = "encrypted"

    fun encryptMessage(
        message: String,
        senderName: String
    ): Message{
        val messageMap = encrypt(message = message)
        return Message(
            content = messageMap[messageKey],
            senderName = senderName,
            messageIv = messageMap[ivKey],
            messageSalt = messageMap[saltKey]
        )
    }

    fun decryptMessage(encryptedMessage: Message): Message {
        if(encryptedMessage.messageSalt == null ||
                encryptedMessage.messageIv == null ||
                encryptedMessage.content == null){
            return encryptedMessage
        }
        val decryptedMessage = decrypt(
            messageSalt = encryptedMessage.messageSalt,
            messageIv = encryptedMessage.messageIv,
            encryptedMessage = encryptedMessage.content
        )
        return Message(
            content = decryptedMessage,
            senderName = encryptedMessage.senderName,
            messageIv = encryptedMessage.messageIv,
            messageSalt = encryptedMessage.messageSalt
        )
    }

    private fun encrypt(message: String): Map<String, String>{
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

        val hashMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mapOf(
                "iv" to Base64.getEncoder().encodeToString(iv),
                "salt" to Base64.getEncoder().encodeToString(salt),
                "encrypted" to Base64.getEncoder().encodeToString(encryptedMessage)
            )
        } else{
            mapOf(
                "iv" to android.util.Base64.encodeToString(iv, android.util.Base64.DEFAULT),
                "salt" to android.util.Base64.encodeToString(salt, android.util.Base64.DEFAULT),
                "encrypted" to android.util.Base64.encodeToString(encryptedMessage, android.util.Base64.DEFAULT),
            )
        }

        return hashMap
    }

    private fun decrypt(
        messageIv: String,
        messageSalt: String,
        encryptedMessage: String
    ):String{
        val salt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(messageSalt)
        } else {
            android.util.Base64.decode(messageSalt, android.util.Base64.DEFAULT)
        }
        val iv = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(messageIv)
        } else {
            android.util.Base64.decode(messageIv, android.util.Base64.DEFAULT)
        }
        val encrypted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(encryptedMessage)
        } else {
            android.util.Base64.decode(encryptedMessage, android.util.Base64.DEFAULT)
        }

        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1234, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decrypted = cipher.doFinal(encrypted)

        Timber.d("Decrypted Message = %s", String(decrypted))
        return String(decrypted)
    }
}