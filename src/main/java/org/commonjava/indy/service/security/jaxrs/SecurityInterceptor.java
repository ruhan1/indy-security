/**
 * Copyright (C) 2022 Red Hat, Inc. (https://github.com/Commonjava/indy-security)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.indy.service.security.jaxrs;

import org.commonjava.indy.service.security.common.SecurityManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@ApplicationScoped
@Provider
public class SecurityInterceptor
        implements ContainerRequestFilter
{
    @Inject
    SecurityManager securityManager;

    @Context
    UriInfo info;

    @Override
    public void filter( ContainerRequestContext requestContext )
    {

        final String path = info.getPath();

        final String method = requestContext.getMethod();

        if ( !securityManager.authorized( path, method ) )
        {
            requestContext.abortWith( Response.status( Response.Status.FORBIDDEN ).build() );
        }
    }
}
