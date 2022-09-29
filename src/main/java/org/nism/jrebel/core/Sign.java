package org.nism.jrebel.core;

import org.nism.jrebel.util.ByteUtil;
import org.nism.jrebel.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * sign
 *
 * @author inism
 */
public class Sign {
    private String signature;

    public void create(String clientRandomness, String guid, boolean offline, long validFrom, long validUntil) {
        //服务端随机数,如果要自己生成，务必将其写到json的serverRandomness中
        List<Object> sList = new ArrayList<>();
        sList.add(clientRandomness);
        sList.add(C.SERVER_RANDOMNESS);
        sList.add(guid);
        sList.add(String.valueOf(offline));
        if (offline) {
            sList.add(validFrom);
            sList.add(validUntil);
        }
        String s2 = StringUtils.join(sList, ";");
        System.err.println(s2);
        final byte[] a2 = Generator.getKey(s2.getBytes());
        this.signature = ByteUtil.convert(a2);
    }

    public String getSignature() {
        return signature;
    }

}
