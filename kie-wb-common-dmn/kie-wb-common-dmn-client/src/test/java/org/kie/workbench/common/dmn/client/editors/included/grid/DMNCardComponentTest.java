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

package org.kie.workbench.common.dmn.client.editors.included.grid;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.dmn.client.editors.included.BaseIncludedModelActiveRecord;
import org.kie.workbench.common.dmn.client.editors.included.DMNIncludedModelActiveRecord;
import org.kie.workbench.common.dmn.client.editors.included.commands.RemoveDMNIncludedModelCommand;
import org.kie.workbench.common.dmn.client.editors.included.commands.RemoveIncludedModelCommand;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class DMNCardComponentTest extends BaseCardComponentTest<DMNCardComponent, DMNCardComponent.ContentView, DMNIncludedModelActiveRecord> {

    @Override
    protected DMNCardComponent.ContentView getCardView() {
        return mock(DMNCardComponent.ContentView.class);
    }

    @Override
    protected DMNCardComponent getCard(final DMNCardComponent.ContentView cardView) {
        return new DMNCardComponent(cardView, refreshDecisionComponentsEvent, sessionCommandManager,
                                    sessionManager,
                                    recordEngine,
                                    client,
                                    refreshDataTypesListEvent);
    }

    @Override
    protected Class<DMNIncludedModelActiveRecord> getActiveRecordClass() {
        return DMNIncludedModelActiveRecord.class;
    }

    @Test
    public void testRefreshView() {

        final DMNIncludedModelActiveRecord includedModel = mock(DMNIncludedModelActiveRecord.class);
        final String path = "/bla/bla/bla/111111111111111222222222222222333333333333333444444444444444/file.dmn";
        final int dataTypesCount = 12;
        final int drgElementsCount = 34;

        when(includedModel.getNamespace()).thenReturn(path);
        when(includedModel.getDataTypesCount()).thenReturn(dataTypesCount);
        when(includedModel.getDrgElementsCount()).thenReturn(drgElementsCount);
        doReturn(includedModel).when(card).getIncludedModel();

        card.refreshView();

        verify(cardView).setPath("...111111222222222222222333333333333333444444444444444/file.dmn");
        verify(cardView).setDataTypesCount(dataTypesCount);
        verify(cardView).setDrgElementsCount(drgElementsCount);
    }

    @Override
    protected BaseIncludedModelActiveRecord prepareIncludedModelMock() {
        return mock(DMNIncludedModelActiveRecord.class);
    }

    @Override
    protected void doCheckRemoveIncludedModelCommandType(final RemoveIncludedModelCommand command) {
        assertTrue(command instanceof RemoveDMNIncludedModelCommand);
    }
}
