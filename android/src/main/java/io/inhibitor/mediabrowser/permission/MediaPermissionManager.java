package io.inhibitor.mediabrowser.permission;


public interface MediaPermissionManager {
    boolean isPermissionGranted(String requestedPermission);

    void requestPermission(String requestedPermission,
                          PermissionGrantedCallback callback);

    boolean onRequestPermissionsResult(String[] requestedPermissions,
                                    int[] grantResults,
                                    int requestCode);
}
