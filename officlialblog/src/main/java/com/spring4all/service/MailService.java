package com.spring4all.service;

public interface MailService {

    /**
     * 发送文本邮件
     *
     * @param subject  邮件主题
     * @param text     邮件主体内容
     * @param receiver
     */
    public void sendText(String subject, String text, String... receiver);

    /**
     *  发送文本和文件
     * @param subject 邮件主题
     * @param text 邮件主体内容
     * @param fileName 文件名
     * @param fileContent 文件内容
     * @param receiver 接收者
     */
    public void sendTextAndFile(String subject, String text, String fileName, String fileContent, String... receiver);
}
