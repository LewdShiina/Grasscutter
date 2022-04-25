package emu.grasscutter.server.packet.send;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Config.GameServerOptions.InGameConsoleOptions;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.GetPlayerFriendListRspOuterClass.GetPlayerFriendListRsp;
import emu.grasscutter.net.proto.HeadImageOuterClass.HeadImage;

public class PacketGetPlayerFriendListRsp extends GenshinPacket {
	
	public PacketGetPlayerFriendListRsp(GenshinPlayer player) {
		super(PacketOpcodes.GetPlayerFriendListRsp);
		
		InGameConsoleOptions consoleOptions = Grasscutter.getConfig().getGameServerOptions().getInGameConsole();
		FriendBrief serverFriend = FriendBrief.newBuilder()
				.setUid(GenshinConstants.SERVER_CONSOLE_UID)
				.setNickname(consoleOptions.Nickname)
				.setLevel(1)
				.setAvatar(HeadImage.newBuilder().setAvatarId(consoleOptions.AvatarId))
				.setWorldLevel(0)
				.setSignature("")
				.setLastActiveTime((int) (System.currentTimeMillis() / 1000f))
				.setNameCardId(consoleOptions.NameCardId)
				.setOnlineState(FriendOnlineState.FRIEND_ONLINE)
				.setParam(1)
				.setUnk1(1)
				.setUnk2(3)
				.build();
		
		GetPlayerFriendListRsp.Builder proto = GetPlayerFriendListRsp.newBuilder().addFriendList(serverFriend);
		
		for (Friendship friendship : player.getFriendsList().getFriends().values()) {
			proto.addFriendList(friendship.toProto());
		}
		for (Friendship friendship : player.getFriendsList().getPendingFriends().values()) {
			if (friendship.getAskerId() == player.getUid()) {
				continue;
			}
			proto.addAskFriendList(friendship.toProto());
		}
		
		this.setData(proto);
	}
}
