#!/usr/bin/env bash

#
# Copyright 2018 New Vector Ltd
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

echo ${TRAVIS_BRANCH}

git status

listOfModifiedFiles=`git diff --name-only HEAD ${TRAVIS_BRANCH}`

echo "List of modified files by this PR:"
echo ${listOfModifiedFiles}

echo Check that the list contains "CHANGES.rst"

if [[ ${listOfModifiedFiles} = *"CHANGES.rst"* ]]; then
  echo "✅ CHANGES.rst has been modified!"
else
  echo "❌ Please add a line describing your change in CHANGES.rst"
  exit 1
fi