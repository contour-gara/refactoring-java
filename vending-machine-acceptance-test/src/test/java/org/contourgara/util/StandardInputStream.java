package org.contourgara.util;

import java.io.InputStream;

public class StandardInputStream extends InputStream {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private StringBuilder sb = new StringBuilder();

    /**
     * 文字列を入力する。改行は自動的に行う。
     *
     * @param str 入力文字列
     */
    public void inputln(String str){
        sb.append(str).append(LINE_SEPARATOR);
    }

    @Override
    public int read() {
        if (sb.isEmpty()) return -1;
        int result = sb.charAt(0);
        sb.deleteCharAt(0);
        return result;
    }
}
