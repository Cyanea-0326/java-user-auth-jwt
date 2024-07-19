#!/bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 -up|-down"
  exit 1
fi

ACTION="$1"
if [ "$ACTION" != "-up" ] && [ "$ACTION" != "-down" ]; then
  echo "Invalid option. Use -up to start or -down to stop."
  exit 1
fi

WORKSPACE_DIR="/home/vscode/workspace"
excluded_dirs=("build" "shared")

# 各プロジェクトのディレクトリに移動して gradle bootRun を実行
echo "------------------- SERVER_MANAGER ----------------------"
for dir in "$WORKSPACE_DIR"/*; do
  # if [ -d "$dir" ] && [ "$(basename "$dir")" != "build" ]; then
  dir_name=$(basename "$dir")
  if [ -d "$dir" ] && [[ ! " ${excluded_dirs[@]} " =~ " ${dir_name} " ]]; then
    cd "$dir" || { echo "Failed to cd into $dir"; exit 1; }

    if [ "$ACTION" == "-up" ]; then
      if [ -f gradle.pid ]; then
        echo "gradle bootRun is already running in $dir"
      else
        echo "Running gradle bootRun in $dir"
        gradle bootRun > gradle.log 2>&1 &  # バックグラウンドで実行し、ログをファイルに出力
        echo $! > gradle.pid  # プロセスIDを保存
        echo "Started gradle bootRun in $dir"
      fi

    elif [ "$ACTION" == "-down" ]; then
      echo "Stopping gradle bootRun in $dir"
      if [ -f gradle.pid ]; then
        PID=$(cat gradle.pid)
        kill "$PID" 
        rm gradle.pid  #プロセスIDを停止,PIDファイルを削除
        echo "Stopped gradle bootRun in $dir"
      else
        echo "No gradle bootRun process found in $dir"
      fi
    fi

    cd "$WORKSPACE_DIR" || { echo "Failed to cd back to $WORKSPACE_DIR"; exit 1; }
  fi
done
echo "---------------------------------------------------------"