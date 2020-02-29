package com.gy.designpattern.state.vote;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 15:08
 */
public class Client {

	public static void main(String[] args) {
		VoteManager voteManager = new VoteManager();
		for (int i = 0; i < 12; i++) {
			voteManager.vote("u1", "A");
		}
	}
}
