package com.citizen.registration.utils

import java.util.regex.Pattern

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/29/2021 at 11:40 AM
 */

/**
 * ...Name validation
 * ...Does not contains numerical value
 * ...Does not contains special characters
 * ...Only alphabetic characters, space, dot will be considered
 */
fun String.nameValidation() : Boolean
{
    if(this.isEmpty()) return false

    if(this == "null" || this == "Null" || this == "NULL") return false

    if(this.contains("<script>") || this.contains("</script>") || this.contains("<?php>") || this.contains("?>"))
        return false

    if(this.matches("-?\\d+(\\.\\d+)?".toRegex()))
        return false

    if(this.length < 3 || this.length > 30)
        return false

    return true
}

/**
 * ...Email Validation
 * ...email should contain numerical value, alphabetic value, alpha numerical value, special characters
 * ...length must be in 8 to 100
 */
fun String.emailValidation() : Boolean
{
    if(this.isEmpty()) return false

    if(this == "null" || this == "Null" || this == "NULL") return false

    if (this.contains("<script>") || this.contains("</script>") || this.contains("<?php") || this.contains("?>"))
        return false

    val regEx = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"

    val pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)

    return matcher.find() && (this.length in 8..100)
}

/**
 * ...Phone validation
 * ...phone number must be valid
 * ...Only BD phone number will be valid
 * ...Phone number length must be 11 or 14(with +88)
 */
fun String.phoneValidation() : Boolean
{
    if(this.isEmpty()) return false

    if(this == "null" || this == "Null" || this == "NULL") return false

    val phone = this.modifyPhoneNumber()

    return when {
        phone.length == 11 -> (phone.startsWith("013") || phone.startsWith("014") || phone.startsWith("015") || phone.startsWith("016") || phone.startsWith("017") || phone.startsWith("018") || phone.startsWith("019"))
        this.length == 14 -> (phone.startsWith("+88013") || phone.startsWith("+88014") || phone.startsWith("+88015") || phone.startsWith("+88016") || phone.startsWith("+88017") || phone.startsWith("+88018") || phone.startsWith("+88019"))
        else -> false
    }
}

/**
 * ...Modify Phone Number
 * ...Replace country code
 */
fun String.modifyPhoneNumber() : String
{
    val phoneNumber = when {
        this.startsWith("0") -> return this
        this.startsWith("+88") -> this.replace("+88","")
        else -> "0${this}"
    }

    return phoneNumber
}

/**
 *** PASSWORD POLICY ***
 * ...password must be at least 4 chars
 */
fun String.passwordValidation() : Boolean
{
    if(this.isEmpty()) return false

    if(this == "null" || this == "Null" || this == "NULL") return false

    if (this.contains("<script>") || this.contains("</script>") || this.contains("<?php") || this.contains("?>"))
        return false

    if(this.length < 6 || this.length > 16)
        return false

    return true
}

/**
 *** PASSWORD POLICY ***
 * ...password must be at least 4 chars
 * ...password and confirmation password must be matched
 */
fun String.passwordValidation(password : String) : Boolean
{
    if(this.isEmpty()) return false

    if(this == "null" || this == "Null" || this == "NULL") return false

    if (this.contains("<script>") || this.contains("</script>") || this.contains("<?php") || this.contains("?>"))
        return false

    if(this.length < 6 || this.length > 16)
        return false

    if(this != password)
        return false

    return true
}

/**
 * ...Any input validation
 */
fun String.anyInputValidation() : Boolean
{
    if(this.isEmpty()) return false

    if(this == "null" || this == "Null" || this == "NULL") return false

    if (this.contains("<script>") || this.contains("</script>") || this.contains("<?php") || this.contains("?>"))
        return false

    return true
}

/**
 * ...Check any amount is getter than zero or not
 * ...return true when amount is getter than zero
 * ...return false when amount is less than zero or equal to zero
 */
fun String.isAmountGetterThanZero() : Boolean
{
    var amount = 0.0
    try {
        amount = this.toDouble()
    }catch (ex : Exception)
    {
        ex.printStackTrace()
    }

    return amount > 0
}

/**
 * ...check provide OTP is valid or not
 * ...OTP is valid only when all are number and length must be 6
 * ...if valid return true
 * ...otherwise return false
 */
fun String.isOtpValid() : Boolean
{
    try {
        if(this.length == 6)
        {
            this.toInt()
            return true
        }
    }catch (ex : Exception)
    {
        return false
    }

    return false
}

/**
 * ...Check any amount is getter than zero or not
 * ...return true when amount is getter than zero
 * ...return false when amount is less than zero or equal to zero
 */
fun Double.isAmountGetterThanZero() : Boolean
{
    return this > 0
}

/**
 * ...Check any amount is getter than zero or not
 * ...return true when amount is getter than zero
 * ...return false when amount is less than zero or equal to zero
 */
fun Int.isAmountGetterThanZero() : Boolean
{
    return this > 0
}

/**
 * ...This will match any 6 digit number in the message
 */
fun String.getOtpFromMessage() : String? {
    var otp : String ?= null
    val pattern = Pattern.compile("(|^)\\d{6}")
    val matcher = pattern.matcher(this)
    if (matcher.find()) otp = "${matcher.group(0)}"

    otp?.let {
        if(!it.isOtpValid())//if otp is not valid
            otp = null//set otp null
    }

    return otp
}