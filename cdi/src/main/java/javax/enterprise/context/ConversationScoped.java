/*
* JBoss, Home of Professional Open Source
* Copyright 2008, Red Hat Middleware LLC, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,  
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package javax.enterprise.context;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>Specifies that a bean is conversation scoped.</p>
 * 
 * <p>The conversation scope is active:</p>
 * 
 * <ul>
 * <li>during all standard lifecycle phases of any JSF faces or 
 * non-faces request.</li>
 * </ul> 
 * 
 * <p>The conversation context provides access to state associated 
 * with a particular conversation. Every JSF request has an 
 * associated conversation. This association is managed 
 * automatically by the container according to the following 
 * rules:</p>
 * 
 * <ul>
 * <li>Any JSF request has exactly one associated conversation.</li>
 * <li>The conversation associated with a JSF request is determined 
 * at the beginning of the restore view phase and does not change 
 * during the request.</li>
 * </ul>
 * 
 * <p>Any conversation is in one of two states: <em>transient</em> 
 * or <em>long-running</em>.</p>
 * 
 * <ul>
 * <li>By default, a conversation is transient</li>
 * <li>A transient conversation may be marked long-running by calling 
 * {@link javax.enterprise.context.Conversation#begin()}</li>
 * <li>A long-running conversation may be marked transient by calling 
 * {@link javax.enterprise.context.Conversation#end()}</li>
 * </ul>
 * 
 * <p>All long-running conversations have a string-valued unique 
 * identifier, which may be set by the application when the 
 * conversation is marked long-running, or generated by the container.</p>
 * 
 * <p>If the conversation associated with the current JSF request 
 * is in the transient state at the end of a JSF request, it is 
 * destroyed, and the conversation context is also destroyed.</p>
 * 
 * <p>If the conversation associated with the current JSF request 
 * is in the long-running state at the end of a JSF request, it is 
 * not destroyed. Instead, it may be propagated to other requests 
 * according to the following rules:</p>
 * 
 * <ul>
 * <li>The long-running conversation context associated with a 
 * request that renders a JSF view is automatically propagated 
 * to any faces request (JSF form submission) that originates 
 * from that rendered page.</li>
 * <li>The long-running conversation context associated with a 
 * request that results in a JSF redirect (a redirect resulting 
 * from a navigation rule or JSF NavigationHandler) is 
 * automatically propagated to the resulting non-faces request, 
 * and to any other subsequent request to the same URL. This is 
 * accomplished via use of a GET request parameter named <tt>cid</tt> 
 * containing the unique identifier of the conversation.</li>
 * <li>The long-running conversation associated with a request 
 * may be propagated to any non-faces request via use of a GET 
 * request parameter named <tt>cid</tt> containing the unique identifier 
 * of the conversation. In this case, the application must manage 
 * this request parameter.</li>
 * </ul>
 * 
 * <p>When no conversation is propagated to a JSF request, the 
 * request is associated with a new transient conversation. All 
 * long-running conversations are scoped to a particular HTTP 
 * servlet session and may not cross session boundaries. In the 
 * following cases, a propagated long-running conversation cannot 
 * be restored and reassociated with the request:</p>
 * 
 * <ul>
 * <li>When the HTTP servlet session is invalidated, all 
 * long-running conversation contexts created during the current 
 * session are destroyed, after the servlet <tt>service()</tt> 
 * method completes.</li>
 * <li>The container is permitted to arbitrarily destroy any 
 * long-running conversation that is associated with no current 
 * JSF request, in order to conserve resources.</li>
 * </ul>
 * 
 * @see javax.enterprise.context.Conversation
 * @see javax.enterprise.context.NonexistentConversationException
 * @see javax.enterprise.context.BusyConversationException
 * 
 * @author Gavin King
 * @author Pete Muir
 */

@Target( { TYPE, METHOD, FIELD })
@Retention(RUNTIME)
@Documented
@NormalScope(passivating = true)
@Inherited
public @interface ConversationScoped
{
}
