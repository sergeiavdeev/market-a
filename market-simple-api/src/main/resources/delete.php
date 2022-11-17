<?php
$base_dir = './files/';
$filename = $_REQUEST['name'];
$folder = $_REQUEST['folder'];

if (isset($_REQUEST['error'])) {
    http_response_code(500);
    return;
}

if (isset($filename)) {
    unlink($base_dir . $folder . '/' . $filename);
} else {

    $dir_for_del = $base_dir . $folder;
    if (file_exists($dir_for_del)) {
        r_rmdir($dir_for_del);
    }
}

function r_rmdir($src)
{
    $dir = opendir($src);
    while (false !== ($file = readdir($dir))) {
        if (($file != '.') && ($file != '..')) {
            $full = $src . '/' . $file;
            if (is_dir($full)) {
                rrmdir($full);
            } else {
                unlink($full);
            }
        }
    }
    closedir($dir);
    rmdir($src);
}