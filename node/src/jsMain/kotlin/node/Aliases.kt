package cz.eidam.kotlinjs.server.node

import node.http.IncomingMessage
import node.http.Server
import node.http.ServerResponse

typealias NodeServer = Server<NodeRequest, NodeResponse>
typealias NodeRequest = IncomingMessage
typealias NodeResponse = ServerResponse<NodeRequest>
