/**
 * For copyright information see the LICENSE document.
 * Adapted from: https://gist.github.com/DeLongey/3757237
 */

package entice.protocol.utils

import akka.event.ActorEventBus
import akka.event.LookupClassification
import akka.actor.ActorRef

import entice.protocol._
 

object MessageBus {
    case class MessageEvent(val sender: ActorRef, val message: Message)
}


/**
 * Message bus to route messages to their appropriate handler actors.
 * This is an implementation of the Reactor design pattern.
 */
class MessageBus extends ActorEventBus with LookupClassification {
 
    import MessageBus._

    type Event = MessageEvent
    type Classifier = String


    protected val mapSize = 10


    protected def classify(event: Event): Classifier = event.message.`type`


    def subscribe(subscriber: Subscriber, msgClazz: Class[_ <: Message]) {
        super.subscribe(subscriber, msgClazz.getSimpleName)
    }


    protected def publish(event: Event, subscriber: Subscriber): Unit = {
        subscriber ! event
    }
}