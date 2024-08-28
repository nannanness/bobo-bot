package org.cherrygirl.pojo;

import net.mamoe.mirai.contact.Member;

/**
 * @author nannanness
 */
public class Nick {
    private String nick;
    private Long groupId;
    private String avatarUrl;
    private long id;

    public static Nick structNick(Member member){
        String nickName = member.getNameCard();
        long groupId = member.getGroup().getId();
        String avatarUrl = member.getAvatarUrl();
        long id = member.getId();
        Nick nick = new Nick(nickName, groupId, avatarUrl, id);
        return nick;
    }

    public Nick(String nick, Long groupId, String avatarUrl, long id) {
        this.nick = nick;
        this.groupId = groupId;
        this.avatarUrl = avatarUrl;
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
