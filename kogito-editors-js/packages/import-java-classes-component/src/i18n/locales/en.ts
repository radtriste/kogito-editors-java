/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { ImportJavaClassesWizardI18n } from "..";
import { en as en_common } from "@kogito-tooling/i18n-common-dictionary";

export const en: ImportJavaClassesWizardI18n = {
  ...en_common,
  modalButton: {
    text: "Import Java classes",
  },
  modalWizard: {
    title: "Import Java classes",
    description:
      "It converts your Java assets to DMN types. This is a one-time import action: if the Java class get updated, you will need to reimport it",
    firstStep: {
      stepName: "Select Java classes",
      input: {
        title: "Search:",
        placeholder: "Search a class name ...",
      },
      emptyState: {
        title: "No Java classes selected",
        body: "Type the Java class name or part of the name to find the one you want to import",
      },
    },
    secondStep: {
      stepName: "Select fields",
    },
    thirdStep: {
      stepName: "Review",
      nextButtonText: "Import",
    },
  },
};
