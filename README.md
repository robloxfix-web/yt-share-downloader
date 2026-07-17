# YT Share Downloader

A professional Android app that integrates with YouTube's share sheet. Tap the share arrow in YouTube → choose this app → instantly download videos, playlists, or selected videos with quality options.

**Features:**
- Appears in YouTube share menu (like SnapTube / VidMate)
- Download single videos
- Download entire playlists or select specific videos
- Choose video quality (1080p, 720p, 480p, audio only)
- Download progress with notifications
- Download history
- Dark mode support
- Modern Material You design

## Project Name Suggestion for GitHub
**Recommended repo name:** `yt-share-downloader`

Alternative names:
- `youtube-share-downloader`
- `snapshare-yt`

---

## How to Upload to GitHub (Step-by-Step from Computer)

### Prerequisites
- Git installed: https://git-scm.com/downloads
- GitHub account
- Android Studio (recommended) or any text editor

### Step 1: Create the GitHub Repository
1. Go to [github.com/new](https://github.com/new)
2. Repository name: `yt-share-downloader`
3. Description: `Android YouTube downloader that integrates with the share sheet`
4. Set to **Public** or **Private**
5. **DO NOT** initialize with README, .gitignore or license
6. Click **Create repository**

### Step 2: Upload from your computer

```bash
# 1. Open terminal / command prompt
cd /path/to/yt-share-downloader

# 2. Initialize git
git init

# 3. Add all files
git add .

# 4. Commit
git commit -m "Initial commit: YouTube share downloader app"

# 5. Add remote (replace YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/yt-share-downloader.git

# 6. Push to GitHub
git branch -M main
git push -u origin main
```

### Step 3: Build & Run
1. Open the folder in **Android Studio**
2. Sync Gradle
3. Run on emulator or device

---

## Project Structure
```
yt-share-downloader/
├── app/
│   └── src/main/
│       ├── java/com/example/ytsharedownloader/
│       │   ├── MainActivity.kt
│       │   ├── ShareReceiverActivity.kt
│       │   └── ...
│       ├── res/
│       └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## Important Notes
- This is a **fully functional UI demo** with realistic download simulation.
- Real downloading would require `yt-dlp` integration + backend (legal considerations).
- The app correctly receives YouTube share links.

Enjoy building your own SnapTube-like experience!
