package io.inhibitor.mediabrowser.permission;


public interface MediaPermissionManager {
    boolean isPermissionGranted(String requestedPermission);

    void requestPermission(String requestedPermission,
                          PermissionGrantedCallback callback);

    void onRequestPermissionsResult(String[] requestedPermissions,
                                    int[] grantResults,
                                    int requestCode);
}
