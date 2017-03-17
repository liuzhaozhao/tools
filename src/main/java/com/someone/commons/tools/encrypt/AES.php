<?php

function pkcs5_pad($text, $blocksize)
{
    $pad = $blocksize - (strlen($text) % $blocksize);
    return $text . str_repeat(chr($pad), $pad);
}
function pkcs5_unpad($text)
{
    $pad = ord($text{strlen($text)-1});
    if ($pad > strlen($text))
    {
        return false;
    }
    if( strspn($text, chr($pad), strlen($text) - $pad) != $pad)
    {
        return false;
    }
    return substr($text, 0, -1 * $pad);
}

function aes_encode($a, $key){
    $td = mcrypt_module_open(MCRYPT_RIJNDAEL_128, '', MCRYPT_MODE_ECB, '');
    $iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_DEV_RANDOM);
    mcrypt_generic_init($td, $key, $iv);
    $b = mcrypt_generic($td, pkcs5_pad($a, 16));
    mcrypt_generic_deinit($td);
    mcrypt_module_close($td);
    return $b;
}

function aes_decode($b, $key){
    $td = mcrypt_module_open(MCRYPT_RIJNDAEL_128, '', MCRYPT_MODE_ECB, '');
    $iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_DEV_RANDOM);
    mcrypt_generic_init($td, $key, $iv);
    $a = pkcs5_unpad(mdecrypt_generic($td, $b));
    mcrypt_generic_deinit($td);
    mcrypt_module_close($td);
    return $a;
}

/*
*  字符串：123456 
	*  使用key：3EFCB15CCC5D525B 	
	*  加密结果为：f82aa9b758b6277660a50ed2482482ce
*/
$a = "123456";
$key = "3EFCB15CCC5D525B";


$b = aes_encode($a, $key);
echo "encoded: ".bin2hex($b), "<br/>";


$b = "1a69a7562ef864cdbc86ddebb4b74cce";
$a = aes_decode(hex2bin($b), $key);
echo "decoded: ".$a, "<br/>";

