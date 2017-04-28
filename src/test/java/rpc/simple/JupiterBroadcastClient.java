/*
 * Copyright (c) 2015 The Jupiter Project
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rpc.simple;

import org.jupiter.rpc.DefaultClient;
import org.jupiter.rpc.DispatchType;
import org.jupiter.rpc.InvokeType;
import org.jupiter.rpc.JClient;
import org.jupiter.rpc.JListener;
import org.jupiter.rpc.consumer.ProxyFactory;
import org.jupiter.rpc.consumer.future.InvokeFuture;
import org.jupiter.rpc.consumer.future.InvokeFutureContext;
import org.jupiter.rpc.consumer.future.InvokeFutureGroup;
import org.jupiter.rpc.load.balance.LoadBalancerType;
import org.jupiter.serialization.SerializerType;
import org.jupiter.transport.UnresolvedAddress;
import org.jupiter.transport.netty.JNettyTcpConnector;

import rpc.api.IOrder;

/**
 * 广播调用客户端
 *
 * jupiter
 * org.jupiter.example.broadcast
 *
 * @author jiachun.fjc
 */
public class JupiterBroadcastClient {

    public static void main(String[] args) {
        JClient client = new DefaultClient().withConnector(new JNettyTcpConnector());

        UnresolvedAddress[] addresses = {
                new UnresolvedAddress("127.0.0.1", 18090),
                new UnresolvedAddress("127.0.0.1", 18091),
                new UnresolvedAddress("127.0.0.1", 18092),
                new UnresolvedAddress("127.0.0.1", 18090),
                new UnresolvedAddress("127.0.0.1", 18091),
                new UnresolvedAddress("127.0.0.1", 18092),
                new UnresolvedAddress("127.0.0.1", 18090)
        };

        for (UnresolvedAddress address : addresses) {
            client.connector().connect(address);
        }

        IOrder service = ProxyFactory.factory(IOrder.class)
        		.group("testGroup").providerName("testProvider").version("1.0.0").client(client)
                .serializerType(SerializerType.HESSIAN)
                .dispatchType(DispatchType.BROADCAST)
                .invokeType(InvokeType.ASYNC)
                .addProviderAddress(addresses)
                .newProxyInstance();

        try {
            // callback方式
            System.out.println("callback-------------------------------");

            service.getOrder();

//            InvokeFutureGroup<IOrder.ResultClass> futureGroup =
//                    InvokeFutureContext.futureBroadcast(IOrder.ResultClass.class);
//            futureGroup.addListener(new JListener<IOrder.ResultClass>() {
//
//                @Override
//                public void complete(IOrder.ResultClass result) {
//                    System.out.print("Callback result: ");
//                    System.out.println(result);
//                }
//
//                @Override
//                public void failure(Throwable cause) {
//                    cause.printStackTrace();
//                }
//            });

            // future.get方式
            System.out.println("future.get-------------------------------");

            service.getOrder();

//            futureGroup =
//                    InvokeFutureContext.futureBroadcast(IOrder.ResultClass.class);
//            for (InvokeFuture<IOrder.ResultClass> f : futureGroup.futures()) {
//                System.out.print("Async result: ");
//                System.out.println(f.getResult());
//            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
