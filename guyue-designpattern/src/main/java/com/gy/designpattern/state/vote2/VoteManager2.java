package com.gy.designpattern.state.vote2;

import com.gy.designpattern.state.vote.VoteState;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class VoteManager2 {

	private VoteState2 voteState;

	/**
	 * 每个用户的投票次数
	 */
	private Map<String, Integer> voteCount = new HashMap<String, Integer>();

	/**
	 * 每个用户的投票结果
	 */
	public Map<String, String> voteResult = new HashMap<String, String>();


	/**
	 * 记录当前每个用户的投票状态对象
	 */
	public Map<String, VoteState2> mapState = new HashMap<String, VoteState2>();

	public void vote(String user, String voteItem) {
		Integer oldValueCount = voteCount.get(user);
		if (oldValueCount == null) {
			oldValueCount = 0;
		}

		oldValueCount = oldValueCount + 1;
		voteCount.put(user, oldValueCount);

		voteState.vote(user, voteItem, this);
	}

	public Map<String, String> getVoteResult() {
		return voteResult;
	}

	public Map<String, Integer> getVoteCount() {
		return voteCount;
	}

	public Map<String, VoteState2> getMapState() {
		return mapState;
	}
}
