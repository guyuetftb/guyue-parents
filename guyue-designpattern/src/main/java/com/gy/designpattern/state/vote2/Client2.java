package com.gy.designpattern.state.vote2;

/**
 * @ClassName Client
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 15:08
 */
public class Client2 {

	public static void main(String[] args) {
		VoteManager2 voteManager = new VoteManager2();
		for (int i = 0; i < 12; i++) {
			voteManager.vote("u1", "A");
		}
	}
}
