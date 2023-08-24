package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.manager.Manager;

import java.util.ArrayList;
import java.util.List;

public class FriendManager extends Manager {

        private final List<String> friends = new ArrayList<>();

        public FriendManager() {
            super("FriendManager");
        }

        public void addFriend(String name) {
            friends.add(name);
        }

        public void removeFriend(String name) {
            friends.remove(name);
        }

        public boolean isFriend(String name) {
            return friends.contains(name);
        }

        public List<String> getFriends() {
            return friends;
        }

        public void setFriends(List<String> friends) {
            this.friends.addAll(friends);
        }
}
