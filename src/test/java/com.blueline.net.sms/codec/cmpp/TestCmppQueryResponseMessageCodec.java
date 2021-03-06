package com.blueline.net.sms.codec.cmpp;

import com.blueline.net.sms.codec.AbstractTestMessageCodec;
import com.blueline.net.sms.codec.cmpp.msg.CmppQueryResponseMessage;
import com.blueline.net.sms.codec.cmpp.packet.CmppHead;
import com.blueline.net.sms.codec.cmpp.packet.CmppQueryResponse;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

public class TestCmppQueryResponseMessageCodec  extends AbstractTestMessageCodec<CmppQueryResponseMessage>{


	@Test
	public void testCodec()
	{
		CmppQueryResponseMessage msg = new CmppQueryResponseMessage(1313);
		msg.setQueryCode("sdf");

		ByteBuf buf = encode(msg);
		ByteBuf copybuf = buf.copy();
		
		int length = buf.readableBytes();
		int expectLength = CmppQueryResponse.QUERYCODE.getBodyLength() +  CmppHead.COMMANDID.getHeadLength();
		
		Assert.assertEquals(expectLength, length);
		Assert.assertEquals(expectLength, buf.readUnsignedInt());
		Assert.assertEquals(msg.getPacketType().getCommandId(), buf.readUnsignedInt());
		Assert.assertEquals(msg.getHeader().getSequenceId(), buf.readUnsignedInt());
		
	
		CmppQueryResponseMessage result = decode(copybuf);
		
		Assert.assertEquals(msg.getHeader().getSequenceId(), result.getHeader().getSequenceId());

		Assert.assertEquals(msg.getQueryCode(), result.getQueryCode());

	}
}
