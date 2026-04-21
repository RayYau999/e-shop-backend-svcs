#!/bin/zsh

# 1. 解析具名參數
for arg in "$@"; do
  case $arg in
    version=*)
      VERSION="${arg#*=}"  # 這裡的 #*= 代表去掉等號左邊的字串
      ;;
    service=*)
      SERVICE_NAME="${arg#*=}"
      ;;
  esac
done

# 2. 設定預設值或檢查必要參數
SERVICE_NAME=${SERVICE_NAME:-"login-service"} # 如果沒給服務名，預設為 login-service

if [ -z "$VERSION" ]; then
    echo "❌ ERROR: Please give the version no.！"
    echo "HELP：./push.sh version=0.0.2 service=payment-service"
    exit 1
fi

# 3. 執行流程
echo "🚀 Handling：$SERVICE_NAME，Version：$VERSION"

# 登入 (假設 PAT 已存在環境變數)
echo $PAT | docker login ghcr.io -u RayYau999 --password-stdin

# 打標籤與推送
docker tag ${SERVICE_NAME}:local ghcr.io/rayyau999/${SERVICE_NAME}:${VERSION}
docker push ghcr.io/rayyau999/${SERVICE_NAME}:${VERSION}

echo "✅ Succeed！"