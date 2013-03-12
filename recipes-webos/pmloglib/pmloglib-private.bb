# Copyright (c) 2013 LG Electronics, Inc.

SUMMARY = "Open webOS logging library - private interface"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PV = "3.0.0-28"
PR = "r0"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig

# OECMAKE_BUILDPATH needs to be different from that of pmloglib so there's
# no collision in the case of local development.
OECMAKE_BUILDPATH = "${S}/BUILD-${PACKAGE_ARCH}-private"
EXTRA_OECMAKE += "-DBUILD_PRIVATE=ON"

WEBOS_GIT_TAG = "submissions/${WEBOS_SUBMISSION}"
SRC_URI = "${OPENWEBOS_GIT_REPO}/pmloglib;tag=${WEBOS_GIT_TAG}"
S = "${WORKDIR}/git"
