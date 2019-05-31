/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.client.widgets.event;

import org.junit.Test;
import org.kie.workbench.common.stunner.core.client.session.ClientSession;
import org.kie.workbench.common.stunner.core.util.EqualsAndHashCodeTestUtils;

import static org.mockito.Mockito.mock;

public class SessionFocusedEventTest {

    @Test
    public void testEqualsAndHashCode() {
        ClientSession session1 = mock(ClientSession.class);
        ClientSession session2 = mock(ClientSession.class);
        EqualsAndHashCodeTestUtils.TestCaseBuilder.newTestCase()
                .addTrueCase(new SessionFocusedEvent(null),
                             new SessionFocusedEvent(null))
                .addTrueCase(new SessionFocusedEvent(session1),
                             new SessionFocusedEvent(session1))
                .addFalseCase(new SessionFocusedEvent(null),
                              new SessionFocusedEvent(session1))
                .addFalseCase(new SessionFocusedEvent(session1),
                              new SessionFocusedEvent(null))
                .addFalseCase(new SessionFocusedEvent(session1),
                              new SessionFocusedEvent(session2))
                .test();
    }
}
