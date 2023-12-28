package com.example.hci_book.util;

public class UserUtils {
    private static boolean isLoggedIn = false;

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static boolean login(String username, String password) {
        // 进行登录验证的逻辑，例如与服务器通信验证用户名和密码
        // 如果验证成功，将isLoggedIn设置为true
        // 如果验证失败，将isLoggedIn设置为false
        isLoggedIn = true;

        return isLoggedIn;
    }

    public static void logout() {
        // 执行注销操作，例如清除登录状态、清除用户数据等
        isLoggedIn = false;
    }

    public static boolean register(String username, String password){
        return true;
    }

    public static boolean resetPassword(String username, String password){
        return true;
    }

    public static boolean getUser(String username){
        return false;
    }
}

