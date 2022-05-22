package dropbox.msgs;

public record UploadFileV2Args( String path, boolean autorename, String mode, boolean mute, boolean strict_conflict) {
}